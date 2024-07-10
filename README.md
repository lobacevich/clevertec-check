<p>Использована java версии 21, Gradle 8.5, PostgreSQL</p>
<p>Перед запуском необходимо прописать в консоли команду<br>
./gradlew clean build</p><br>
После этого перейти в каталог build/libs<br>
cd ./build/libs<br>
<p>Приложение запускается с помощью консольной команды:<br>
java -jar clevertec-check.jar id-quantity discountCard=xxxx 
balanceDebitCard=xxxx pathToFile=xxxx saveToFile=xxxx 
datasource.url=ххх datasource.username=ххх datasource.password=ххх<br>
где:<br>
id - идентификатор товара. id могут повторяться: 1-3 2-5 1-1 тоже, что и 1-4 2-5<br>
quantity - количество товара<br>
discountCard=xxxx - название и номер дисконтной карты<br>
balanceDebitCard=xxxx - баланс на дебетовой карте<br>
pathToFile - включает относительный (от корневой директории проекта) путь +
название файла с расширением (всегда присутствует в заданном формате)<br>
saveToFile - включает относительный (от корневой директории проекта) путь +
название файла с расширением<br>
datasource.url - url адрес для подключения к бд
datasource.username - имя пользователя
datasource.password - пароль
Примечание:<br>
● всё указываем через пробел<br>
● id-quantity - добавляем префикс id-(количество товара). В наборе параметров
должна быть минимум одна такая связка "id-quantity"<br>
● discountCard=xxxx - добавляем префикс discountCard=(любые четыре цифры)<br>
● balanceDebitCard=xxxx - указываем префикс balanceDebitCard=(любая сумма<br>
на счете). Обязательно должна присутствовать. Баланс может быть как и с
двумя знаками после запятой, так и отрицательный<br>
● Eсли передан saveToFile, тогда чек сохраняется по пути из saveToFile(включая ошибки), 
иначе в result.csv в корне проекта

Пример:
java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java 3-1 2-5 5-1 discountCard=1111
balanceDebitCard=100 saveToFile=./result.csv datasource.url=jdbc:postgresql://localhost:5432/check 
datasource.username=postgres datasource.password=postgres

По команде выше формируется CSV-файл по пути, переданном в параметре saveToFile, содержащий в 
себе список товаров и их количество с ценой, а также рассчитанную сумму с учетом скидки по 
предъявленной карте, если она есть.
Расшифровка команды: (3-1) 3 - это товар с id = 3, 1 - количество (1шт);
тоже самое с (2-5) id=2 в количестве 5 штук, (5-1) id=5 - одна штука и т. д.;
discountCard=1111 - означает, что была предъявлена скидочная карта с номером 1111. 
Файлы со списком продуктов будут загружаться из файла ./src/main/resources/products.csv, 
чек будет сохраняться в файл ./result.csv</p>
<p>DDL/DML операции хранятся в файле src/main/resources/data.sql</p>