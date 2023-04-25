package com.zjq.dailyrecord.algorithm.list;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class BankNode {
        String coder;
        String pcoder;
        String name;
        List<BankNode> children;
    }
