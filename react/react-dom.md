
## React (Virtual) DOM Terminology (리엑트 (가상) 돔)
> [React (Virtual) DOM Terminology]
(https://facebook.github.io/react/docs/glossary.html)를 번역한 것 입니다. 리엑트 용어에 관한 설명중 가상 돔(virtual DOM)에 대해 설명합니다. 번역을 잘하는것도, 리액트를 잘하는 것도 아니고 개인적 공부를 위함이라 부족한 점이 많습니다. 이상하거나 잘못된점이 알려주시면 감사하겠습니다. 


## React (Virtual) DOM 용어

리엑트에 나오는 용어 중에 눈여겨 봐야할 중요한 핵심적인 개념이 다섯가지 있습니다.

* ReactElement / ReactElement Factory
* ReactNode
* ReactComponet / ReactComponent Class

### React Elements
리엑트의 주요 타입은 **ReactElement** 입니다. ReactElemen는 
4가지의 속성을 가지고 있는데 *type* , *props*, *key*, *ref* 가 그것입니다. ReactElement는 메소드를 가지고 있지 않아서 프로토타입할것도 없습니다. 

당신은 *React.createElement* 를 통해서 객체를 생성할 수 있습니다.

~~~javascript
 var root = React.createElement('div');
~~~

DOM에 새로운 트리를 만들기 위해, 당신은 *ReactElements*를 생성하고 일반적인 DOM Element(HTMLElement 또는 SVGElement)와 함께 *ReactDOM.render* 에 전달합니다.*ReactElement* DOM Elements와 혼동하지 않습니다. *ReactElement*는 가볍고, stateless고, 불변인 가상으로 표현된 DOM element입니다. 이것은 virtual DOM 입니다.

~~~javascript
 ReactDOM.render(root, document.getElementById('example'));
~~~

DOM element에 속성을 추가하기 위해, 속성 객체를 두번째 인자로, 자식을 세번째 인자로 전달합니다.

~~~javascript
 var child = React.createElement('li', null, 'Text Content');
 var root = React.createElement('ul', { className: 'my-list' }, child);
 ReactDOM.render(root, document.getElementById('example'));
~~~

만약 당신이 React JSX 를 사용한다면, 이러한 *ReactElements*는 당신을 위해 생성됩니다. 따라서 이것은 아래와 같습니다.

~~~javascript
 var root = <ul className="my-list">
             <li>Text Content</li>
           </ul>;
 ReactDOM.render(root, document.getElementById('example'));
~~~

### Factorie
*ReactElement-factory*는 특정한 속성을 가지고 있는 *ReactElement*를 생성하는 단순한 함수입니다. 리엑트는 당신이 팩토리를 생성할수 있는 내장 helper를 가지고 있습니다. 이것은 단지 아래와 같습니다.

~~~javascript
 function createFactory(type) {
  return React.createElement.bind(null, type);
}
~~~

팩토리는 항상 *React.createElement('div')*라고 타이핑하는것이 아니라 편리한 방법을 제공합니다.

~~~javascript
 var div = React.createFactory('div');
 var root = div({ className: 'my-div' });
 ReactDOM.render(root, document.getElementById('example'));
~~~

리엑트는 일반적인 HTML 태그를 위한 내장 팩토리도 가지고 있습니다.
~~~javascript
 var root = React.DOM.ul({ className: 'my-list' },
             React.DOM.li(null, 'Text Content')
           );
~~~

만약 당신이 JSX를 사용하고 있다면, 당신은 팩토리가 필요없습니다. JSX는 이미 *ReactElements*를 생성하는 편리한 방법을 제공하고 있기 때문입니다.


### React Nodes
*ReactNode*는 아래와 같은것들이 될수 있습니다.
* ReactElement
* string(ReactText 라고 알려진)
* number(ReactText 라고 알려진)
* array of *ReactNodes*(ReactFragment 라고 알려진)

이는 자식들을 대신하기위해 다른 *ReactElements* 의 속성들로써 사용될 수 있습니다. 그리고 이들은 효과적으로 *ReactElements*의 트리를  생성합니다.

### React Components
ekdtlsdms 



