package view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final Scanner sc = new Scanner(System.in);

    public int buyMoneyInput() {
        int buyMoney;
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
        return buyMoney;
    }

    public List<Integer> luckyNumberInput() {
        System.out.println("\n당첨 번호를 입력해 주세요.");
        while (true) {
            try {
                return parseLuckyInput();
            } catch (IllegalArgumentException e) {
                printError(e);
            }
        }
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

    public int bonusNumberInput() {
        System.out.println("\n보너스 번호를 입력해 주세요.");
        while (true) {
            try {
                int bonus = sc.nextInt();
                if (bonus < 1 || bonus > 45) {
                    throw new IllegalArgumentException("범위 내의 숫자를 입력하세요.");
                }
                return bonus;
            } catch (IllegalArgumentException e) {
                printError(e);
            }
        }
    }

    private static void printError(Exception e) {
        System.out.println("[ERROR] " + e.getMessage());
    }
}
