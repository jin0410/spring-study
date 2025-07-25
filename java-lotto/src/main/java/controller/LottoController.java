package controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import repository.LottoRepository;
import service.LottoService;
import view.InputView;
import view.OutputView;

public class LottoController {

    private static final Scanner sc = new Scanner(System.in);

    public void execute(LottoRepository lottoRepository, LottoService lottoService, InputView inputView,
                        OutputView outputView) {

        int buyMoney;
        int buyAmount;

        System.out.println("구입금액을 입력해 주세요.");
        while (true) {
            try {
                buyMoney = sc.nextInt();
                if (buyMoney < 0 || buyMoney % 1000 != 0) {
                    throw new IllegalArgumentException("유효하지 않은 값입니다");
                }
                break;
            } catch (IllegalArgumentException e) {
                printError(e);
            }
        }
        buyAmount = buyMoney / 1000;
        System.out.println("\n" + buyAmount + "개를 구매했습니다.");

        lottoService.buyLotto(buyAmount).forEach((lottoList) ->
                System.out.println(lottoList));

        sc.nextLine();
        System.out.println("\n당첨 번호를 입력해 주세요.");
        while (true) {
            try {
                lottoService.setLuckyNumbers(parseLuckyInput());
                break;
            } catch (IllegalArgumentException e) {
                printError(e);
            }
        }

        System.out.println("\n보너스 번호를 입력해 주세요.");
        while (true) {
            try {
                int bonus = sc.nextInt();
                if (bonus < 1 || bonus > 45) {
                    throw new IllegalArgumentException("범위 내의 숫자를 입력하세요.");
                }
                lottoService.setBonusNumber(bonus);
                break;
            } catch (IllegalArgumentException e) {
                printError(e);
            }
        }

        Map<Integer, Integer> resultMap = lottoService.getResult();
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

    private static List<Integer> parseLuckyInput() {
        String input = sc.nextLine();
        List<Integer> inputList = new ArrayList<>();
        for (String s : input.split(",")) {
            inputList.add(Integer.parseInt(s));
        }
        inputList.forEach((number) -> {
            if (number < 1 || number > 45) {
                throw new IllegalArgumentException("범위 내의 숫자를 입력하세요.");
            }
        });
        if (inputList.size() != 6) {
            throw new IllegalArgumentException("여섯 개의 숫자를 입력하세요.");
        }
        inputList.sort(Comparator.naturalOrder());
        return inputList;
    }

    private static void printError(Exception e) {
        System.out.println("[ERROR] " + e.getMessage());
    }
}
