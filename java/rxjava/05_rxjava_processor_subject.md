# 5장 Processor 와 Subject
## Processor와 Subject

- 프로세서는 Publisher와 Subscriber 인터페이스를 모두 상속하는 인터페이스
- 프로세서 인터페이스는 소비자로서 데이터를 받고 이 데이터를 생산자로서 통지하는 역할
- 프로게서가 Publicsher를 구독하면 Publisher가 통지하는 데이터를 받는 소비자가 되며, 동시에 Processor를 Subscriber가 구독하면 데이터를 생산하는 생산자가 됨.

### Processor, Subject의 종류

- Processor , subject들은 통지된 데이터를 캐시하고 통지하는 방법이 각각 달라 구분해서 사용해야함.

**PublischProcessor , PublishSubject**

데이터를 받은 시점에 데이터 통지

**BehaviorProcessor, Behavior Subjcet**

마지막으로 받은 데이터를 캐시하고 구독 시점에 캐시한 데이터를 바로 통지. 그 이후 부터는 데이터를 받으 시점에 통지

**ReplayProcessor, RelaySubject**

모든 데이터를 캐시하고 구독시점에 캐시한 데이터를 바로 통지

**AsyncProcessor, AsyncSubeject**

완료 통지를 받았을때 마지막으로 받은데이터만 통지

**UnicasetProcessor, UnicastSubject**

하나의 소비자만 구독

### FlowableProcess, Subject 의메서드

**toSeriallized**

- 하나의 Proceccor, Subject는 서로다른 스레드에서 동시에 통지하는 것을 허용하지 않엄.
- 여러 스레드에서 동시에 통지 메서드를 호출하면 구독하는 소비자에게 통지가 순차적으로 가지않아 위험함
- 하여 Serialized클래스를 제공해, 여러 스레드에서 동시에 통지메서드를 호출해도 스레드 안전한 처리르할 수 있는 기능지원.