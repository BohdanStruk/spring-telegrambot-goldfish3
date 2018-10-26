package goldfish.express;

 import goldfish.express.entity.UserEntity;
 import goldfish.express.entity.enums.UserRole;
 import goldfish.express.repository.UserRepository;
 import org.apache.http.HttpEntity;
 import org.apache.http.HttpResponse;
 import org.apache.http.client.HttpClient;
 import org.apache.http.client.entity.UrlEncodedFormEntity;
 import org.apache.http.client.methods.HttpPost;
 import org.apache.http.impl.client.HttpClientBuilder;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.CommandLineRunner;
 import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 import org.springframework.boot.builder.SpringApplicationBuilder;
 import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
 import org.springframework.security.crypto.password.PasswordEncoder;

 import java.io.IOException;
 import java.io.InputStream;

@SpringBootApplication
public class SpringBotApplication implements CommandLineRunner
{


	public static void main(String[] args) {
		SpringApplication.run(SpringBotApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public void run(String... args) throws Exception{
		initiateWebHook();

		if(userRepository.count() == 0)  {
			UserEntity userEntity = new UserEntity();
			userEntity.setId((long) 1);
			userEntity.setFirstName("Admin");
			userEntity.setLastName("Admin");
			userEntity.setPhoneNumber("admin");
			userEntity.setRole(UserRole.ROLE_ADMIN);
			userEntity.setPassword(passwordEncoder.encode("admin"));

			userRepository.save(userEntity);
		}
	}

	public void initiateWebHook(){
		MessageSender messageSender = new MessageSender("https://b9efc40a.ngrok.io");
		messageSender.execute();
	}
}

