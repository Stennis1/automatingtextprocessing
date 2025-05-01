package com.example.automatingtextprocessing.model;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TextAnalysis {

    public static Map<String, Long> wordFrequency(String text) {
        return Arrays.stream(text.toLowerCase().split("\\W+")).filter(word -> !word.isEmpty()).
                collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }


    public static List<Map.Entry<String, Long>> topNWords(Map<String, Long> freqMap, int n) {
        return freqMap.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(n).collect(Collectors.toList());
    }


    public static long sentenceCount(String text) {
        return Arrays.stream(text.split("[.!?]")).filter(
                sentence -> !sentence.trim().isEmpty()).count();
    }
}
