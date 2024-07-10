<p>Использована java версии 21</p>
<p>Перед запуском необходимо прописать в консоли команду<br>
javac -d src ./src/main/java/ru/clevertec/check/*.java</p>
<p>Приложение запускается с помощью консольной команды:<br>
java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java id-quantity discountCard=xxxx
balanceDebitCard=xxxx pathToFile=xxxx saveToFile=xxxx<br>
где:<br>
id - идентификатор товара. id могут повторяться: 1-3 2-5 1-1 тоже, что и 1-4 2-5<br>
quantity - количество товара<br>
discountCard=xxxx - название и номер дисконтной карты<br>
balanceDebitCard=xxxx - баланс на дебетовой карте<br>
pathToFile - включает относительный (от корневой директории проекта) путь +
название файла с расширением (всегда присутствует в заданном формате)<br>
saveToFile - включает относительный (от корневой директории проекта) путь +
название файла с расширением<br>
Примечание:<br>
● Название и путь CSV-файла: result.csv в корне проекта<br>
● всё указываем через пробел<br>
● id-quantity - добавляем префикс id-(количество товара). В наборе параметров
должна быть минимум одна такая связка "id-quantity"<br>
● discountCard=xxxx - добавляем префикс discountCard=(любые четыре цифры)<br>
● balanceDebitCard=xxxx - указываем префикс balanceDebitCard=(любая сумма<br>
на счете). Обязательно должна присутствовать. Баланс может быть как и с
двумя знаками после запятой, так и отрицательный<br>
● Eсли передан saveToFile, тогда чек сохраняется по пути из saveToFile(включая ошибки), 
иначе в result.csv

Пример:
java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java 3-1 2-5 5-1 discountCard=1111
balanceDebitCard=100 pathToFile=./src/main/resources/products.csv saveToFile=./result.csv
По команде выше формируется CSV-файл (result.csv) в корне
проекта, содержащий в себе список товаров и их количество с ценой, а также
рассчитанную сумму с учетом скидки по предъявленной карте, если она есть.
Расшифровка команды: (3-1) 3 - это товар с id = 3, 1 - количество (1шт);
тоже самое с (2-5) id=2 в количестве 5 штук, (5-1) id=5 - одна штука и т. д.;
discountCard=1111 - означает, что была предъявлена скидочная карта с номером 1111. 
Файлы со списком продуктов будут загружаться из файла ./src/main/resources/products.csv, 
чек будет сохраняться в файл ./result.csv</p>