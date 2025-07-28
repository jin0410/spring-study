package service;

import etc.Randoms;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.Lotto;
import repository.LottoRepository;

public class LottoService {
    // field
    // luckyNumbers
    private List<Integer> luckyNumbers;
    // bonusNumber
    private int bonusNumber = 0;
    // lottoRepository
    private LottoRepository lottoRepository;

    // constructor
    // public LottoService(LottoRepository lottoRepository) {
//        this.lottoRepository = lottoRepository;
//    }


    public void setLottoRepository(LottoRepository lottoRepository) {
        this.lottoRepository = lottoRepository;
    }

    // method
    // buyLotto(int) // int : 단순개수
    public List<List<Integer>> buyLotto(int amount) {
        List<List<Integer>> result = new ArrayList<>();
        do {
            result.add(insertNewLotto());
        } while (--amount > 0);
        return result;
    }

    // insertNewLotto()
    private List<Integer> insertNewLotto() {
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
        Lotto newLotto = new Lotto(numbers);
        lottoRepository.insertLotto(newLotto);
        return numbers;
    }

    // setLuckyNumbers(list<int>)
    public void setLuckyNumbers(List<Integer> luckyNumbers) {
        Set<Integer> luckyNumbersSet = new HashSet<>(luckyNumbers);
        if (luckyNumbersSet.size() != 6) {
            throw new IllegalArgumentException();
        }
        luckyNumbersSet.forEach((number) -> {
            if (number > 45 || number < 1) {
                throw new IllegalArgumentException();
            }
        });
        this.luckyNumbers = luckyNumbers;
    }

    // setBonusNumber(int)
    public void setBonusNumber(int bonusNumber) {
        if (luckyNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("보너스 번호는 당첨 번호와 겹칠 수 없습니다.");
        }
        this.bonusNumber = bonusNumber;
    }

    // getLuckyNumbers()
    public List<Integer> getLuckyNumbers() {
        return luckyNumbers;
    }

    // getBonusNumber()
    public int getBonusNumber() {
        return bonusNumber;
    }

    // HashMap<int,int> getResult()
    public Map<Integer, Integer> getResult() {
        if (bonusNumber == 0 || luckyNumbers.isEmpty()) {
            throw new IllegalArgumentException("당첨번호와 보너스 번호가 존재해야 합니다");
        }
        List<Lotto> lottoList = lottoRepository.getLottoList();
        Map<Integer, Integer> resultMap = buildDefaultResultMap();
        for (Lotto currentLotto : lottoList) {
            int rank = getRank(currentLotto);
            if (rank > 5) {
                continue;
            }
            resultMap.put(rank, resultMap.get(rank) + 1);
        }
        return resultMap;
    }

    private int getRank(Lotto currentLotto) {
        int matchResult = matchLuckyNumbers(currentLotto);
        if (matchResult == 6) {
            return 1;
        }
        boolean bonusResult = matchBonusNumber(currentLotto);
        if (matchResult == 5 && bonusResult) {
            return 2;
        }
        return 8 - matchResult;
    }

    private Map<Integer, Integer> buildDefaultResultMap() {
        Map<Integer, Integer> resultMap = new HashMap<>();
        for (int i = 1; i <= 5; ++i) {
            resultMap.put(i, 0);
        }
        return resultMap;
    }

    private boolean matchBonusNumber(Lotto currentLotto) {
        return currentLotto.getNumbers().contains(bonusNumber);
    }

    private int matchLuckyNumbers(Lotto currentLotto) {
        List<Integer> numbers = currentLotto.getNumbers();
        int result = 0;
        for (int number : luckyNumbers) {
            if (numbers.contains(number)) {
                ++result;
            }
        }
        return result;
    }
}
