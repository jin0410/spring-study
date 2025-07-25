import static org.assertj.core.api.Assertions.assertThat;

import etc.Randoms;
import java.util.ArrayList;
import java.util.List;
import model.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.LottoRepository;

public class LottoRepositoryTest {

    LottoRepository lottoRepository = new LottoRepository();

    List<Lotto> createRandomLottoList(int amount) {
        List<Lotto> newLottoList = new ArrayList<>();
        do {
            newLottoList.add(new Lotto(Randoms.pickUniqueNumbersInRange(1, 45, 6)));
        } while (--amount > 0);
        return newLottoList;
    }

    @Test
    @DisplayName("입력받은 로또를 list에 저장한다")
    void save_lotto() {
        // given
        List<Lotto> newLottoList = createRandomLottoList(3);

        // when
        newLottoList.forEach((lotto) -> lottoRepository.insertLotto(lotto));

        // then
        assertThat(lottoRepository.getLottoList()).hasSize(3);
        assertThat(lottoRepository.getLottoList()).isEqualTo(newLottoList);
    }
}
