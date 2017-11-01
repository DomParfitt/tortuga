package visualiser;

import automata.FiniteStateMachine;
import lexer.TokenType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FSMController {

    @RequestMapping("/fsm")
    public FiniteStateMachine finiteStateMachine() {
        FiniteStateMachinePOJO pojo = new FiniteStateMachinePOJO(TokenType.IDENTIFIER.getMachine());
        return TokenType.IDENTIFIER.getMachine();
    }

    @RequestMapping("/hello")
    public String hello() {
        return "greeting";
    }

}
