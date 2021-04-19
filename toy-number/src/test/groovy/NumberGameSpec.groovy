import game.NumberGame
import spock.lang.Specification

class NumberGameSpec extends Specification {

    def "랜덤 넘버를 입력 받아 객체를 생성 한다."() {
        when:
        def result = new NumberGame(1, 0)

        then:
        result != null
    }

    def "play 할 때마가 tryCount가 올라간다."() {
        given:
        def result = new NumberGame(1, 0)

        when:
        result.play(2)
        result.play(1)

        then:
        result.getTryCount() == 2
    }

    def "play 시 정답보다 크면 크다, 작으면 작다, 맞추면 정답이라고 응답한다."() {
        given:
        def game = new NumberGame(10, 0)

        expect:
        game.play(number) == result

        where:
        number | result
        1 | "작다"
        11 | "크다"
        10 | "정답"
    }
}
