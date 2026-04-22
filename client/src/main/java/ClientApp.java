import com.common.Request;
import console.Console;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
    public ClientApp(Console console, Terminal terminal, UDPClient udpClient, RequestManager requestManager) {
        this.console = console;
        this.terminal = terminal;
        this.udpClient = udpClient;
        this.requestManager = requestManager;
    }

    /**
     * Запускает приложение
     */
    public void run() throws IOException {
        List<String> commandNames = Arrays.asList(
                "help", "info", "show", "add", "update", "remove_by_id",
                "clear", "execute_script", "exit", "add_if_max",
                "remove_lower", "history", "average_of_distance",
                "filter_contains_name", "print_descending"
        );

        StringsCompleter completer = new StringsCompleter(commandNames);

        LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(completer)
                .build();

        InputProvider consoleProvider = new JLineInputProvider(lineReader);

        console.printByProgram("Добро пожаловать! Введите 'help' для получения списка команд.");

        while (true) {
            String commandLine;
            try {
                commandLine = lineReader.readLine("> ");
                if (commandLine.trim().isEmpty()) {
                    continue;
                }
                requestManager.processCommand(commandLine, consoleProvider);

            } catch (UserInterruptException | EndOfFileException e) {
                console.println("\nДо свидания!");
                return;
            } catch (ServerUnavailableException e) {
                console.printErr(e.getMessage() + ". Попробуйте позже.");
            } catch (Exception e) {
                console.printErr("Ошибка: " + e.getMessage());
            }
        }
    }
}