document.addEventListener('DOMContentLoaded', () => {
  // Находим кнопку отправки по её идентификатору
  const sendBtn = document.querySelector("#sendBtn");

  // Находим поле ввода сообщения по его идентификатору
  const inputMessage = document.querySelector("#inputMessage");

  // Находим элемент, где будет отображаться вывод
  const output = document.querySelector("#output");

  // Добавляем обработчик события "клик" на кнопку отправки
  sendBtn.addEventListener('click', () => {
      // Получаем текст сообщения из поля ввода
      const messageText = inputMessage.value;

      // Подготавливаем данные для отправки на сервер
      const dataToSend = {
          message: messageText,
      };

      // Отправляем запрос на сервер методом POST
      fetch('/game', {
          method: 'POST', // Метод запроса
          headers: {
              'Content-Type': 'application/json', // Указываем тип содержимого - JSON
          },
          body: JSON.stringify(dataToSend), // Преобразуем объект в строку JSON
      })
      .then((response) => {
          // Проверяем, успешен ли ответ
          if (!response.ok) {
              throw new Error(`Ошибка! Статус: ${response.status}`); // Генерируем ошибку, если статус не OK
          }
          return response.json(); // Парсим ответ как JSON
      })
      .then((data) => {
          // Обновляем содержимое элемента вывода
          output.innerHTML = `<p>Статус: ${data.status}</p><p>Сообщение: ${data.echoMessage}</p>`;
      })
      .catch((error) => console.error(error)); // Ловим ошибки и выводим их в консоль
  });
});
