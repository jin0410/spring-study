package controller;

import repository.LottoRepository;
import service.LottoService;
import view.InputView;
import view.OutputView;

public class LottoController {


    private final LottoService lottoService;
    //private final LottoRepository lottoRepository;
    private final InputView inputView;
    private final OutputView outputView;

    public LottoController(LottoRepository lottoRepository, LottoService lottoService, InputView inputView,
                           OutputView outputView) {
        //this.lottoRepository = lottoRepository;
        this.lottoService = lottoService;
        this.inputView = inputView;
        this.outputView = outputView;
        lottoService.setLottoRepository(lottoRepository);
    }


    public void execute() {

        int buyMoney;
        int buyAmount;

        buyMoney = inputView.buyMoneyInput();
        buyAmount = buyMoney / 1000;

        System.out.println("\n" + buyAmount + "개를 구매했습니다.");

        lottoService.buyLotto(buyAmount).forEach((lottoList) ->
                System.out.println(lottoList));

        lottoService.setLuckyNumbers(inputView.luckyNumberInput());

        lottoService.setBonusNumber(inputView.bonusNumberInput());

        outputView.resultOutput(lottoService.getResult(), buyMoney);

    }

}
