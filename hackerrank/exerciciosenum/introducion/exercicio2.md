## Exercicio 2

A maioria dos desafios do HackerRank exige que você leia a entrada de stdin (entrada padrão) e grave a saída em stdout (saída padrão).

Uma maneira popular de ler a entrada do stdin é usar a classe Scanner e especificar o Input Stream como System.in. Por exemplo:

Scanner scanner = new Scanner(System.in);
String minhaString = scanner.next();
int myInt = scanner.nextInt();
scanner.close();

System.out.println("minhaString é: " + minhaString);
System.out.println("minhaInt é: " + minhaInt);
O código acima cria um objeto Scanner chamado e o utiliza para ler uma String e um int. Em seguida, ele fecha o objeto Scanner porque não há mais entrada para ler e imprime em stdout usando System.out.println(String). Então, se nossa entrada for:

Oi 5
Nosso código imprimirá:

myString é: Oi
meuInt é: 5
Como alternativa, você pode usar a classe BufferedReader.

Tarefa
Neste desafio, você deve ler inteiros de stdin e depois imprimi-los em stdout. Cada inteiro deve ser impresso em uma nova linha. Para tornar o problema um pouco mais fácil, uma parte do código é fornecida para você no editor abaixo.

Formato de entrada

Existem linhas de entrada e cada linha contém um único inteiro.

Entrada de amostra

42
100
125
Saída de amostra

42
100
125