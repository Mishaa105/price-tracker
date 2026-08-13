package io.github.Mishaa105.price_tracker.controller;

import io.github.Mishaa105.price_tracker.dto.product.offer.ProductOfferResponse;
import io.github.Mishaa105.price_tracker.dto.search.PlayStationSearchResponse;
import io.github.Mishaa105.price_tracker.dto.search.Product;
import io.github.Mishaa105.price_tracker.infrastructure.client.JsoupClient;
import io.github.Mishaa105.price_tracker.infrastructure.extractor.impl.ProductOfferExtractor;
import io.github.Mishaa105.price_tracker.infrastructure.extractor.impl.SearchPageExtractor;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ConsoleController
{
    public enum States
    {
        SEARCH, CHOOSE, SHOW_PRODUCT
    }

    Scanner scanner = new Scanner(System.in);

    private final JsoupClient jsoupClient;
    private final SearchPageExtractor searchPageParser;
    private final ProductOfferExtractor productPageParser;

    public void start()
    {
        States state = States.SEARCH;
        String request = "";
        String command;
        String region = "/en-us/";
        String id = "";
        PlayStationSearchResponse responseSearch = null;
        ProductOfferResponse responseProduct;
        List<Product> productsList = List.of();

        while (true)
        {
            switch (state)
            {
                case SEARCH ->
                {
                    System.out.println("Введите ваш запрос (break, чтобы выйти)");

                    request = scanner.nextLine();

                    if (Objects.equals(request, "break"))
                    {
                        return;
                    }

                    Document htmlDoc = jsoupClient.loadHtmlPage(region, JsoupClient.RequestType.SEARCH, request);
                    String jsonData = searchPageParser.extractJson(htmlDoc);
                    responseSearch = searchPageParser.deserialize(jsonData);
                    productsList = new ArrayList<>(responseSearch.props().apolloState().getProducts());

                    state = States.CHOOSE;
                }

                case CHOOSE ->
                {
                    System.out.println("По запросу " + request + " найдены следующие товары:");

                    Collection<Product> products = responseSearch.props().apolloState().getProducts();
                    int n = 1;
                    for (int i = products.size() - 1; i >=0; i--)
                    {
                        Product product = productsList.get(i);
                        System.out.println(n + ": " + product.name());
                        n++;
                    }

                    System.out.println("Для выбора товара введите его номер, а для возврат в режим поиска введите return");

                    command = scanner.nextLine();

                    if (Objects.equals(command, "return"))
                    {
                        state = States.SEARCH;
                    } else
                    {
                        int choice = Integer.parseInt(command);
                        id = productsList.get(productsList.size() - choice).id();
                        state = States.SHOW_PRODUCT;
                    }
                }

                case SHOW_PRODUCT ->
                {
                    System.out.println("Страница выбранного вами товара");

                    Document htmlDoc = jsoupClient.loadHtmlPage(region, JsoupClient.RequestType.PRODUCT, id);
                    String jsonData = productPageParser.extractJson(htmlDoc);
                    responseProduct = productPageParser.deserialize(jsonData);

                    String name = responseProduct.cache().gameName().invariantName();
                    double originalPrice = 0;
                    double discountPrice = 0;

                    System.out.println(name);
                    String data = "";

//                    if (!(responseProduct.cache().priceData().isEmpty()))
//                    {
//                        originalPrice = (double) responseProduct.cache().basePrice().local().telemetryMeta().skuDetail().skuPriceDetail().getFirst().originalPriceValue() / 100;
//                        discountPrice = (double) responseProduct.cache().basePrice().local().telemetryMeta().skuDetail().skuPriceDetail().getFirst().discountPriceValue() / 100;
//                        data = responseProduct.cache().basePrice().local().offerAvailability();
//                    }

                    System.out.println("Обычная цена: " + originalPrice + "$");
                    System.out.println("Цена по скидке : " + discountPrice + "$");

                    System.out.println("Распродажа продлится до " + data);

                    System.out.println("Если хотите вернуться на предыдущий этап напишите return");
                    System.out.println("Если хотите завершить сеанс напишите break");

                    command = scanner.nextLine();

                    if (Objects.equals(command, "return"))
                    {
                        state = States.CHOOSE;
                    }
                    else if (Objects.equals(command, "break"))
                    {
                        return;
                    }
                }
            }
        }
    }
}

// Если при поиске null (ps plus), то скрываем и идем дальше не передвигая индекс
