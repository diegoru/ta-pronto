package br.com.sinqia.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    private long id;
    private String description;
    private List<ItemStock> produtos = new ArrayList<>();
}
