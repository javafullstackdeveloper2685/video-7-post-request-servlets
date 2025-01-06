# POST-запросы в Java Servlets 🚀 Fetch API + JSON

Этот проект демонстрирует, как работать с POST-запросами в Java Servlets, используя Fetch API и JSON. Вы узнаете, как отправить данные с клиента на сервер, обработать их и вернуть ответ.

## 📌 Что в этом проекте:
- **Работа с Fetch API**: Простое и современное решение для отправки POST-запросов из JavaScript.
- **Java Servlets**: Прием, обработка и отправка данных в формате JSON.
- **Пошаговое руководство**: От настройки Maven WebApp до обработки запросов.

## 🛠️ Используемые технологии:
- **Java**
- **Java Servlets**
- **Maven**
- **Fetch API**
- **IntelliJ IDEA Community Edition**

## 🚀 Как запустить проект:

1. **Склонируйте репозиторий:**
   ```bash
   git clone https://github.com/javafullstackdeveloper2685/video-7-post-request-servlets.git
   ```

2. **Откройте проект в IntelliJ IDEA:**
   - Убедитесь, что у вас установлен сервер Tomcat.
   - Настройте Tomcat в разделе "Run Configurations".

3. **Соберите и запустите проект:**
   - Создайте артефакт WAR через Maven:
     ```bash
     mvn clean package
     ```
   - Разверните его на сервере Tomcat.

4. **Откройте браузер:**
   Перейдите по адресу:
   ```bash
   http://localhost:8080/index.html
   ```

5. **Протестируйте POST-запрос:**
   - Введите данные на странице и отправьте их на сервер.
   - Проверьте ответ в консоли браузера.

## 🔍 Основной код проекта:

### 1. HTML и Fetch API (client-side):
```html
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>POST-запросы</title>
</head>
<body>
    <h1>Отправка POST-запроса</h1>
    <input type="text" id="message" placeholder="Введите сообщение">
    <button id="send">Отправить</button>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const sendBtn = document.querySelector("#send");
            const inputMessage = document.querySelector("#message");
            const output = document.querySelector("#output");

            const sendMessage = async () => {
                const messageText = inputMessage.value.trim();

                if (!messageText) {
                    output.innerHTML = `<p style="color: red;">Введите сообщение!</p>`;
                    return;
                }

                try {
                    sendBtn.disabled = true; // Блокируем кнопку
                    const response = await fetch('/your-webapp-context/YourServlet', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({ message: messageText })
                    });

                    if (!response.ok) {
                        throw new Error(`Ошибка! Статус: ${response.status}`);
                    }

                    const data = await response.json();
                    output.innerHTML = `<p>Статус: ${data.status}</p><p>Сообщение: ${data.echoMessage}</p>`;
                } catch (error) {
                    output.innerHTML = `<p style="color: red;">Ошибка: ${error.message}</p>`;
                } finally {
                    sendBtn.disabled = false; // Разблокируем кнопку
                }
            };

            sendBtn.addEventListener('click', sendMessage);
        });
    </script>

    <div id="output"></div>
</body>
</html>
```

### 2. Java Servlet (server-side):
```java
@WebServlet("/YourServlet")
public class YourServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Чтение JSON из тела запроса
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        // Преобразование JSON в объект
        Gson gson = new Gson();
        RequestData requestData = gson.fromJson(sb.toString(), RequestData.class);

        // Создание ответа
        ResponseData responseData = new ResponseData("OK", "Получено: " + requestData.getMessage());
        String jsonResponse = gson.toJson(responseData);

        // Отправка JSON-ответа клиенту
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
}

class RequestData {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

class ResponseData {
    private String status;
    private String echoMessage;

    public ResponseData(String status, String echoMessage) {
        this.status = status;
        this.echoMessage = echoMessage;
    }
}
```

## 🎯 Чему вы научитесь:
- Как отправлять данные через POST-запрос с использованием Fetch API.
- Как обрабатывать JSON-запросы на стороне сервера.
- Как возвращать ответы в формате JSON.

## 📈 Следующие шаги:
- Подключение базы данных для хранения сообщений.
- Разработка Docker Compose для развертывания приложения.
- Создание RESTful API для взаимодействия с клиентом.

---

💡 Если у вас есть вопросы, пишите в комментариях или создавайте Issue. Удачи в программировании! 🚀
