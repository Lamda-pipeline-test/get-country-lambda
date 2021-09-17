package com.revature.get_countries;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CountryRepository {

    private final DynamoDBMapper dbReader;

    public CountryRepository() {
        dbReader = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public List<Country> getAllCountries() {
        return dbReader.scan(Country.class, new DynamoDBScanExpression());
    }

    public Optional<Country> getCountryByName(String isbn) {

        Map<String, AttributeValue> queryInputs = new HashMap<>();
        queryInputs.put(":isbn", new AttributeValue().withS(isbn));

        DynamoDBScanExpression query = new DynamoDBScanExpression()
                .withFilterExpression("isbn = :isbn")
                .withExpressionAttributeValues(queryInputs);

        List<Country> queryResult = dbReader.scan(Country.class, query);

        return queryResult.stream().findFirst();

    }

}
