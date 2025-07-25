import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import model.Lotto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.LottoRepository;
import service.LottoService;

public class LottoServiceTest {

    LottoRepository lottoRepository = new LottoRepository();
    LottoService lottoService = new LottoService(lottoRepository);

    void insertDefaultLottoToRepository() {
        lottoRepository.insertLotto(new Lotto(List.of(1, 2, 3, 4, 5, 6)));
        lottoRepository.insertLotto(new Lotto(List.of(1, 2, 3, 4, 5, 7)));
        lottoRepository.insertLotto(new Lotto(List.of(1, 2, 3, 4, 5, 8)));
        lottoRepository.insertLotto(new Lotto(List.of(1, 2, 3, 4, 7, 8)));
        lottoRepository.insertLotto(new Lotto(List.of(1, 2, 3, 7, 8, 9)));
        lottoRepository.insertLotto(new Lotto(List.of(1, 2, 7, 8, 9, 10)));
    }

    @AfterEach
    void tearDown() {
        lottoRepository.clearLotto();
    }

    @Test
    @DisplayName("개수대로 로또가 생성되어야 한다")
    void create_lotto() {
        // given & when
        lottoService.buyLotto(5);

        // then
        assertThat(lottoRepository.getLottoList()).hasSize(5);
    }

    @Test
    @DisplayName("당첨번호와 보너스번호가 있을 때, 결과를 정확히 계산해야 한다")
    void calculate_correct_result() {
        // given
        insertDefaultLottoToRepository();

        // when
        lottoService.setLuckyNumbers(List.of(1, 2, 3, 4, 5, 6));
        lottoService.setBonusNumber(7);

        // then
        Map<Integer, Integer> result = lottoService.getResult();
        assertThat(result).hasSize(5);
        result.forEach((rank, amount) -> assertThat(amount).isEqualTo(1));
    }

    @Test
    @DisplayName("당첨번호가 없으면 오류가 발생해야 한다")
    void result_error_with_no_lucky_number() {
        // given
        insertDefaultLottoToRepository();

        // when
        lottoService.setBonusNumber(7);

        // then
        assertThatThrownBy(() -> lottoService.getResult())
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("보너스번호가 없으면 오류가 발생해야 한다")
    void result_error_with_no_bonus_number() {
        // given
        insertDefaultLottoToRepository();

        // when
        lottoService.setLuckyNumbers(List.of(1, 2, 3, 4, 5, 6));

        // then
        assertThatThrownBy(() -> lottoService.getResult())
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("당첨 번호를 정확히 저장해야 한다")
    void lucky_number_get_set() {
        // given
        List<Integer> newLuckyNumber = List.of(5, 6, 7, 8, 9, 10);

        // when
        lottoService.setLuckyNumbers(newLuckyNumber);

        // then
        assertThat(lottoService.getLuckyNumbers()).isEqualTo(newLuckyNumber);
    }

    @Test
    @DisplayName("잘못된 당첨 번호 입력 시 오류가 발생해야 한다.")
    void lucky_number_set_error() {
        // given
        List<Integer> newLuckyNumber1 = List.of(5, 6, 7, 8, 9, 46);
        List<Integer> newLuckyNumber2 = List.of(5, 6, 7, 8, 9, 9);

        // when & then
        assertThatThrownBy(() -> lottoService.setLuckyNumbers(newLuckyNumber1))
                .isInstanceOf(Exception.class);
        assertThatThrownBy(() -> lottoService.setLuckyNumbers(newLuckyNumber2))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("보너스 번호를 정확히 저장해야 한다")
    void bonus_number_get_set() {
        //given & when
        lottoService.setBonusNumber(39);

        // then
        assertThat(lottoService.getBonusNumber()).isEqualTo(39);
    }

}
