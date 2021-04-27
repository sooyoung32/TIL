# 생성자에 매개변수가 많다면 빌더를 고려하라

정적 팩터리와 생성자에는 같은 제약이 있는데, 선택적 매개변수가 많을때 대응이 어렵다.
매개변수 많아지면 클라이언트 코드를 작성하거나 읽기 어렵다.

점층적 생성자 패턴의 안전성과 자바 빈즈 패턴의 가독성을 겸비한 빌더 패턴!

객체를 직접 만드는 대신 필수 매개변수만으로 생성자를 호츨해 빌더 객체를 얻는다.

```java

public class NutritionFacts {
    
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;
    
    public static class Builder {
        private final int servingSize;
        private final int servings;
        
        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }
        
        public Builder calories(int val) {
            calories = val;
            return this;
        }

        public Builder fat(int val) {
            fat = val;
            return this;
        }
        
        public Builder sodium(int val) {
            sodium = val;
            return this;
        }

        public Builder carbohydrate(int val) {
            carbohydrate = val;
            return this;
        }
        
        public NutritionFacts(Builder builder) {
            servingSize = builder.servingSize;
            servings = builder.servings;
            calories = builder.calories;
            fat = builder.fat;
            sodium = builder.sodium;
            carbohydrate = builder.carbohydrate;
        }
        
    }
    
}
```

빌더패턴을 사용하면 쓰고 쉽고 읽기 쉽고.
잘못된 매개변수 일찍 발견하려면, 빌더의 생성자와 메서드에서 입력 배개변수를 검사하고, build 메서드가 호출하는 새엇ㅇ자에서 여러 매개변수에 걸친 불변식(invariant)을 검사하자.

- 불변 : 어떠한 변경도 허용하지 않는다는 뜻
- 불변식 : 프로그램이 실행되는 동안 혹은 정해진 기간동안 반드시 만족해야하는 조건 

### 핵심정리
생성자나 정적 팩터리가 처리해야할 매개변수가 많다면 빌더 패턴을 선택하는게 더 낫다. 매개변수 중 다수가 필수가 아니거나, 같은 타입이면 더 그렇다. 빌더는 점층적 생성자보다 클라이언트 코드를 읽고 쓰기가 훨씩 간결하고 자바빈즈보다 안전하다.
