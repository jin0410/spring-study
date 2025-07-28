import controller.LottoController;
import repository.LottoRepository;
import service.LottoService;
import view.InputView;
import view.OutputView;

public class LottoApplication {
    public static void main(String[] args) {
        LottoController lottoController = new LottoController(new LottoRepository(), new LottoService(),
                new InputView(), new OutputView());
        lottoController.execute();

    }
}
