# Simulador de Robo
Repositorio utilizado para entrega de laboratórios da matéria MC322.

## Colaboradores
Felipe Pavanello Capovilla - 174411 <p>
Guilherme Henrique da Silva - 281217

# Adições
## LAB 4
- Implementação de interfaces
  - Entidade: Obstaculo e Robo
  - Comunicavel: RoboTerestre RoboVoadorExplorador
  - Sensoriavel: RoboVoadorExplorador
  - Battery: RoboAereo
  - Attacker: RoboVeiculo
  - Destructible: RoboVeiculo e RoboPedestre
- Criação de Exceptions personalizadas
  - ColisaoException: Ambiente, RoboAereo e Robo
  - ErroComunicacaoException: RoboVoadorExplorador, RoboTerrestre
  - LowBatteryException: RoboAereo
  - PointOutOfMapException: Ambiente, Robo, SensorEspacial
  - RoboDesligadoException: Robo, RoboPedestre, RoboVeiculo
  - SensorMissingException: RoboVoadorExplorador, RoboAereo
  - ValueOutOfBoundsException: RoboVoadorExplorador, RoboAereo, RoboVoadorTurista, RoboTerrestre, RoboVeiculo, SensorAltitude, SensorTemperatura
  - ZeroLifePointsException: RoboPedestre, RoboVeiculo
- Tornar o console modularizável
- Adição de Tarefas para: RoboVoadorExplorador, RoboVoadorTurista, RoboVeiculo e RoboPedestre
## LAB 5
- Implementação de arquivos de input/output
  - Ler de entrada no terminal
  - Ler aruivo txt: entrada de configurações
  - Escrever arquivo txt: saída de registros de log
- Instanciação de objetos na classe main por leitura de arquivo txt
- Adição de AgenteInteligente's: robos que consseguem realizar uma missão automaticamente, registrando o processo em um log
- Adição de missões para AgenteInteligente's:
  - Missão de buscar ponto: cria um ponto final aleatório que o robo deve chegar
  - Missão de patrulha: cria um caminho quadrado que o robo deve percorrer
  - Missão de monitorar: usuário escolhe um ponto final, o robo detecta todas as entidades que passou no caminho
- Adição de módulos para robos
  - Módulo de ControleMovimento
  - Módulo de GerenciadorSensores
  - Módulo de Comunicação

# Manual
## Execução
Comandos usados dentros de uma das pastas labXX.
```
javac -sourcepath src src/**/*.java -d bin
```
```
java -cp bin main/Main
```

## Instruções do MENU
1. **MENU AMBIENTE** - ações relacionada ao ambiente
   - Impressão do mapa do ambiente na altura indicada, mostrando os robos e obstáculos
   - Informações do ambiente, como dimensões, robos ativos e obstáculos presentes
2. **MENU ROBO** - lista de robos ativos, escolher qual deseja controlar
   - RoboAereoStandart
     - deslocamento no plano
     - deslocamento da altura (subir[+]/descer[-])
     - imprimir o ambiente no raio de alcance do sensor
     - monitorar estado dos sensores
     - carregar bateria
     - Executar missão
     - informações do robo
     - desligar robo
   - RoboAereoExplorador
     - deslocamento no plano
     - deslocamento da altura (subir[+]/descer[-])
     - imprimir o ambiente no raio de alcance do sensor
     - Iniciar/Finalizar missão, indicando o planeta destino, velocidade, temperatura e pressão
     - Iniciar Tarefa (realizar missão)
     - monitorar estado dos sensores
     - carregar robo
     - enviar mensagem
     - Executar missão
     - informações do robo
     - ligar/desligar sensores
     - desligar robo
   - RoboAereoTurista
     - deslocamento no plano
     - deslocamento da altura (subir[+]/descer[-])
     - imprimir o ambiente no raio de alcance do sensor
     - Iniciar/Finalizar passeio, indicando cidade turistica e número de passageiros
     - inicar tarefa (realizar passeio)
     - monitorar estado dos sensores
     - carregar robo
     - Executar missão
     - informações do robo
     - desligar robo
   - RoboTerrestreStandart
     - deslocamento no plano
     - imprimir o ambiente no raio de alcance do sensor
     - Definir a velocidade máxima de deslocamento do robo
     - monitorar estado dos sensores
     - enviar mensagem
     - informações do robo
     - desligar robo
   - RoboVeiculo
     - deslocamento no plano, para frente ou para trás
     - mudar a velocidade de deslocamento
     - Virar a direção do robo
     - imprimir o ambiente no raio de alcance do sensor
     - Definir a velocidade máxima de deslocamento do robo
     - Mudar número de passageiros
     - iniciar tarefa (realizar 1-5 ataque bem-sucedidos)
     - atacar na frente
     - reparar robo
     - monitorar estado dos sensores
     - enviar mensagem
     - informações do robo
     - desligar robo
   - RoboPedestre
     - deslocamento no plano. Correndo(100% velocidade máxima) ou Andando (60% velocidade máxima), deslocando-se menos quanto mais peso carrega 
     - imprimir o ambiente no raio de alcance do sensor
     - Definir a velocidade máxima de deslocamento do robo
     - reparar robo
     - monitorar estado dos sensores
     - iniciar tarefa (coletar 1-8 caixas no mapa)
     - enviar mensagem
     - informações do robo
     - desligar robo

## Diagrama UML
Diagrama de classes e relações do projeto.

![Diagrama](lab05/lab05.drawio.png)

## Arquivos
#### Main
- **Main.java** - arquivo principal que instancia os principais objetos e forma as relações entre eles;
#### Ambiente
- **Ambiente.java** - simulador do ambiente virtual no qual estão estabelecidos os objetos `Robo` e `Obstaculo`;
- **Obstaculo.java** - objeto imovel do ambiente que pode bloquear a passagem de `Robo`;
#### Console
- **Console.java** - interface com usuário através do terminal;
- **MenuItem.java** - modelo para guardar as informações de uma opção do menu;
#### Robo
- **Robo.java** - classe mais geral do `Robo` que possui os métodos mais amplos;
  - **AgenteInteligente.java** - classe de `Robo` que possui missões que são realizadas automaticamente;
    - **RoboAereo.java** - classe geral de `Robo` voador, implementando o movimento vertical;
      - **RoboVoadorExplorador.java** - classe especialista de `RoboAereo`que inclui missões para planetas;
      - **RoboVoadorTurista.java** - classe especialista de `RoboAereo`que inclui passeios turisticos para cidades;
  - **RoboTerrestre.java** - classe geral de `Robo` voador, implementando o velocidade máxima;
    - **RoboVeiculo.java** - classe especialista de `RoboTerretre`que inclui movimento apenas no sentido da direção do robo;
    - **RoboPedestre.java** - classe especialista de `RoboTerretre`que inclui movimento de andar e correr, com modificações com o peso;
#### Sensor
- **Sensor.java** - classe de sensor padrão sem utilidade especifica incluido nos objetos `Robo`;
  - **SensorAltitude.java** - sensor especializado em detectar a altura do `Robo`;
  - **SensorEspacial.java** - sensor que monitora o espaço em volta do `Robo` num quadrado de lado 2 x raio;
  - **SensorTemperatura.java** - sensor que mede a temperatura do `Robo`;
#### Central Comunicação
- **CentralComunicacao.java** - objeto intermediário das comunicações entre classes que implementam `Comunicavel` 
#### Interfaces
- **Entidade.java** - interface para objetos entidade;
- **Attacker.java** - interface para `Entidade` atacar `Destructible`;
- **Destructible.java** - interface para `Entidade` que pode ser destruído;
- **Battery.java** - interface para `Entidade` que possui bateria;
- **Comunicavel.java** - interface para `Entidade` que pode se comunicar com outros `Comunicavel`;
- **Sensoriavel.java** - interface para `Entidade` que podem ligar e desligar sensores;
- **Missao.java** - interface para tipo de missão para `AgenteInteligente`;
#### Constantes
- **Bussola.java** - enum das quatro direções da bússola;
- **TipoObstaculo.jaava** - enum de tipos de `Obstaculo` padrão, determinando se não atravessáveis.
- **EstadoRobo.java** - enum do estado do `Robo`: LIGADO ou DESLIGADO;
- **TipoEntidade.java** - enum dos tipos de `Entidade` que existem;
#### Exceptions
- **ColisaoException.java** - excessão ocorre quando duas `Entidade`s no mesmo local do ambiente;
- **ErroComunicacaoException.java** - excessão ocorre quando há um erro durante a comunicação;
- **LowBatteryException.java** - excessão ocorre quando uma `Entidade` que implementa `Battery` tenta realizar uma ação que gasta mais energia do que possui;
- **PointOutOfMapException.java** - excessão ocorre quando tenta acessar um ponto fora do ambiente;
- **RoboDesligadoException.java** - excessão ocorre quando tenta realizar uma ação com `Robo` enquanto está DESLIGADO;
- **SensorMissingException.java** - excessão ocorre quando tenta acessar um sensor que não está instalado;
- **ValueOutOfBoundsException.java** - excessão ocorre quando uma variável recebe um valor fora de um intervalo determinado pelo código;
- **ZeroLifePointsException.java** - excessão ocorre quando uma `Entidade` que implementa `Destructible` tenta realizar uma ação enquanto não possui vida;
- **NoModuleException.java** - excessão ocorre quando tenta acessar um modulo que não existe;
- **NoRobotException.java** - excessão ocorre quando tenta acessar um robo que não existe;
### IOfiles
- **LeitorConfiguracao.java** - classe que lê o arquivo `config.txt` e instancia o ambiente e entidades dele;
- **OutputLog.java** - classe que escreve no arquivo `logRegister.txt`, utilizado para registrar os logs de `Missao`;
- **ScannerGlobal.java** - classe que instancia um scanner de terminal para ser chamado por qualquer arquivo;
### Missão
- **MissaoBuscarPonto.java** - `Missao` que cria um ponto final aleatório que um `AgenteInteligente` deve chegar automaticamente;
- **MissaoMonitorar.java** - `Missao` que o usuário encvia o ponto final que o `AgenteInteligente` deve chegar, detectando todas as `Entidade` que passa no raio do `SensorEspacial`;
- **MissaoPatrulhar.java** - `Missao` que cria um caminho quadrado que o `AgenteInteligente` deve percorrer;
- **PontoCaminho.java** - classe auxiliar para o `Missao` especializadas;
### Modulos
- **ControleMovimento.java** - modulo que controla o movimento de um `Robo` associado;
- **GerenciadorSensore.java** - modulo que gerencia os sensores de um `Robo` associado;
- **ModuloComunicacao.java** - modulo que gerencia a comunicação de um `Comunicavel` associado;
### Resources
- **config.txt** - arquivo de configuração do sistema, onde localiza a instância de todos os objetos;
- **logRegister.java** - arquivo de registro dos logs de `Missao` de `AgenteInteligente`;





## Sobre
**IDE Utilizada:** Visual Studio Code <p>
**Versão do Java:** 11.0.26
