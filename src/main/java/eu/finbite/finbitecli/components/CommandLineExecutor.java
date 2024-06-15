package eu.finbite.finbitecli.components;

import eu.finbite.finbitecli.services.InputHandlerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("!test")
@AllArgsConstructor
public class CommandLineExecutor implements CommandLineRunner {
    private InputHandlerService scannerInputHandler;

    @Override
    public void run(String... args) {
        log.info("EXECUTING : command line runner");
        scannerInputHandler.handleUserInput();
    }
}
