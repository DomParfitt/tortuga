package visualiser;

import automata.FiniteStateMachine;
import lexer.TokenType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FSMController {

    @RequestMapping("/fsm")
    public FiniteStateMachine finiteStateMachine() {
        FiniteStateMachinePOJO pojo = new FiniteStateMachinePOJO(TokenType.IDENTIFIER.getMachine());
        return TokenType.IDENTIFIER.getMachine();
    }

}
