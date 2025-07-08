package com.store.streamsql.stream.optional;

import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class StreamOptional {
  private   Stream<Integer> numbers
          = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    public void start(){
        optional1IfPresent();
    }

    private void optional1IfPresent(){
        numbers.filter(n -> n % 2 == 0)
                .findFirst()
                .ifPresent(System.out::println);
    }

    private void optional2(){
        numbers.parallel()
                .filter(n -> n % 2 == 0)
                .findAny()
                .ifPresent(System.out::println);
    }

    private void optional3(){
        boolean hasEvenNumber = numbers.anyMatch(n -> n % 2 == 0);

        if (hasEvenNumber) {
            System.out.println("Є хоча б одне парне число.");
        } else {
            System.out.println("Немає жодного парного числа.");
        }
    }

    private void optional4(){

    }
}
