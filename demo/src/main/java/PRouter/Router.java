package PRouter;



import java.io.IOException;
import java.net.SocketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Router {
	
	@Autowired
	private RouterService RSer;	

	@RequestMapping("/Router/Connect")
	public String Connect() throws SocketException, IOException {
		
		RouterService.ResposeCommand="";
		RSer.connect();
		return "Establish connection is established";
		
	}
	
	@RequestMapping("/Router/{Command}")
	public String getRespose(@PathVariable String Command) {

		RSer.sendCommand(Command);
		
		return RouterService.ResposeCommand;

	}
	}


	
