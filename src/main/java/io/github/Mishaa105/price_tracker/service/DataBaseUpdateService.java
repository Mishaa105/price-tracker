package io.github.Mishaa105.price_tracker.service;

import io.github.Mishaa105.price_tracker.batch.ProductBatch;
import io.github.Mishaa105.price_tracker.db.entity.CurrentPrice;
import io.github.Mishaa105.price_tracker.db.entity.Offer;
import io.github.Mishaa105.price_tracker.db.entity.Price;
import io.github.Mishaa105.price_tracker.db.entity.Product;
import io.github.Mishaa105.price_tracker.db.repository.CurrentPriceRepository;
import io.github.Mishaa105.price_tracker.db.repository.OfferRepository;
import io.github.Mishaa105.price_tracker.db.repository.PriceRepository;
import io.github.Mishaa105.price_tracker.db.repository.ProductRepository;
import io.github.Mishaa105.price_tracker.dto.allproducts.AllProducts;
import io.github.Mishaa105.price_tracker.dto.allproducts.Concept;
import io.github.Mishaa105.price_tracker.dto.media.ProductMediaResponse;
import io.github.Mishaa105.price_tracker.dto.product.Local;
import io.github.Mishaa105.price_tracker.dto.product.PlayStationProductResponse;
import io.github.Mishaa105.price_tracker.dto.product.SkuPriceDetail;
import io.github.Mishaa105.price_tracker.enums.Regions;
import io.github.Mishaa105.price_tracker.infrastructure.JsoupClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataBaseUpdateService
{
    private final ObjectMapper mapper;
    private final JsoupClient jsoupClient;
    private final ProductPageHtmlParser productPageParser;
    private final ProductMediaHtmlParser productMediaParser;
    private final ProductRepository productRepository;
    private final PriceRepository allPriceRepository;
    private final CurrentPriceRepository currentPriceRepository;
    private final OfferRepository offerRepository;

    private List<String> getListOfProductsId(String rawJson)
    {
        AllProducts batch = mapper.readValue(rawJson, AllProducts.class);
        List<Concept> listOfProduct = batch.data().categoryGridRetrieve().getListOfProducts();
        List<String> listOfId = new ArrayList<>();
        for (Concept product : listOfProduct)
        {
            if(product.getId() != null)
            {
                listOfId.addAll(product.getId());
            }
        }
        return listOfId;
    }

    private PlayStationProductResponse getProductData(String region, String id)
    {
        Document htmlDoc = jsoupClient.loadHtmlPage(region, JsoupClient.RequestType.PRODUCT, id);
        String jsonData = productPageParser.extractJson(htmlDoc);
        return productPageParser.deserialize(jsonData);
    }

    private String getProductPreviewUrl(String region, String id)
    {
        Document htmlDoc = jsoupClient.loadHtmlPage(region, JsoupClient.RequestType.PRODUCT, id);
        String jsonData = productMediaParser.extractJson(htmlDoc);
        ProductMediaResponse deserializedData = productMediaParser.deserialize(jsonData);
        return deserializedData.getPreviewUrl();
    }

    private ProductBatch buildDatabaseEntities(PlayStationProductResponse productData, String previewUrl)
    {
        List<Local> localList = productData.getListOfAvailableLocals();
        ProductBatch batch = new ProductBatch();

        for (Local local : localList)
        {
            for(SkuPriceDetail price : local.telemetryMeta().skuDetail().skuPriceDetail())
            {
                String name = productData.getName();
                String invariantName = productData.getInvariantName();
                String productId = local.ctaDataTrack().sku();
                int basePrice = price.originalPriceValue();
                int discountPriceValue = price.discountPriceValue();
                String offerBranding = price.offerBranding();
                String offerAvailability = local.offerAvailability();
                String priceCurrencyCode = price.priceCurrencyCode();

                Product product = new Product(productId, name, invariantName, previewUrl);
                Offer offer = new Offer(null, null, offerAvailability);
                CurrentPrice currentPrice = new CurrentPrice(basePrice, discountPriceValue, offerBranding, priceCurrencyCode);
                Price allPrices = new Price(basePrice, discountPriceValue, offerBranding, priceCurrencyCode);

                currentPrice.setProduct(product);
                currentPrice.setOffer(offer);
                allPrices.setProduct(product);
                allPrices.setOffer(offer);

                batch.getProducts().add(product);
                batch.getOffers().add(offer);
                batch.getCurrentPrices().add(currentPrice);
                batch.getPrices().add(allPrices);
            }
        }
        return batch;
    }

    private void saveBatchToDb(ProductBatch batch)
    {
        productRepository.saveAll(batch.getProducts());
        offerRepository.saveAll(batch.getOffers());
        currentPriceRepository.saveAll(batch.getCurrentPrices());
        allPriceRepository.saveAll(batch.getPrices());
    }

    public String testProduct(String string)
    {
        List<String> list = getListOfProductsId(string);
        String id = list.getFirst();
        return getProductData(Regions.US.getRegionCode(), id).toString();
    }

    public List<String> testId(String string)
    {
        return getListOfProductsId(string);
    }

    public void bdSaveTest(String string)
    {
        List<String> list = getListOfProductsId(string);
        String id = list.getFirst();
        PlayStationProductResponse productResponse = getProductData(Regions.US.getRegionCode(), id);
        String previewUrl = getProductPreviewUrl(Regions.US.getRegionCode(), id);
        ProductBatch batch = buildDatabaseEntities(productResponse, previewUrl);
        saveBatchToDb(batch);
    }

}
