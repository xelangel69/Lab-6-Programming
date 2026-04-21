import com.common.Request;
import console.Console;

import java.io.IOException;

import com.common.exceptions.FailedCommandExecutionException;
import util.InputProvider;
import util.JLineInputProvider;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;

/**
 * Мененджер приложения - управляет всем приложением
 * @author Ivan Kirillov
 */
public final class ClientApp {
    private final Console console;
    private final Terminal terminal;
    private final UDPClient udpClient;
    private RequestManager requestManager;
    private InputProvider inputProvider;

    /**
     * Конструктор мененджера приложения
     */
    public ClientApp(Console console, Terminal terminal, UDPClient udpClient) {
        this.console = console;
        this.terminal = terminal;
        this.udpClient = udpClient;
    }

    /**
     * Запускает приложение
     */
    public void run() throws IOException {
        StringsCompleter completer = new StringsCompleter();

        LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(completer)
                .build();

        InputProvider consoleProvider = new JLineInputProvider(lineReader);

        while (true) {
            String commandLine;
            try {
                commandLine = lineReader.readLine("> ");
                requestManager.processCommand(commandLine, inputProvider);
            } catch (UserInterruptException | EndOfFileException e) {
                console.println("\nДо свидания!");
                return;
            } catch (InterruptedException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            if (commandLine.trim().isEmpty()) {
                continue;
            }
        }
    }
}