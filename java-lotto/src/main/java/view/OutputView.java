package view;

import java.util.List;
import java.util.Map;

public class OutputView {
    public void resultOutput(Map<Integer, Integer> resultMap, int buyMoney) {
        List<Long> winnings = List.of(2000000000L, 30000000L, 1500000L, 50000L, 5000L);
        long earned = 0;
        for (int i = 0; i < 5; ++i) {
            earned += winnings.get(i) * resultMap.get(i + 1);
        }
        double yield = Math.round((double) earned / buyMoney * 1000.0) / 10.0;
        System.out.println("\n당첨 통계\n---");
        System.out.println("3개 일치 (5,000원) - " + resultMap.get(5) + "개");
        System.out.println("4개 일치 (50,000원) - " + resultMap.get(4) + "개");
        System.out.println("5개 일치 (1,500,000원) - " + resultMap.get(3) + "개");
        System.out.println("5개 일치, 보너스 볼 일치 (30,000,000원) - " + resultMap.get(2) + "개");
        System.out.println("6개 일치 (2,000,000,000원) - " + resultMap.get(1) + "개");
        System.out.println("총 수익률은 " + yield + "%입니다.");
    }
}
