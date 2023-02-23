package com.queries.movie.specificQuery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MyQuery {

    private String fieldName;
    private String value;
    private SearchOperator operator;


    public MyQuery(){}


}
