

이 문서는 [What is npm](https://docs.npmjs.com/getting-started/what-is-npm)을 번역한 것입니다. 번역에 능숙하지 않기 때문에 오류가 있을 수 있습니다 (~~당연히 있습니다~~). 오류를 발견시에는 덧글로 제게 알려주시면 정말 감사하겠습니다.

# What is npm?

npm은 자바스크립트 개발자들이 코드를 재사용하고 공유하는 것뿐 아니라 당신이 공유 한 코드를 업데이트 하는 것 또한 쉽게 해줍니다.

만약 당신이 어느정도 자바스크립트를 사용했다면 아마 당신은 npm을 들어봤을 것입니다. npm은 자바스크립트 개발자들이 그들의 특정 문제를 해결하기위해 작성했던 코드를 공유하고 다른 개발자들이 그 코드를 그들의 어플리케이션에 재사용하는 것을 편리하게 제공해줍니다.

그리고 다신이 다른사람의 코드를 사용하고 있다면, npm은 그 코드가 업데이트가 있었는지 정말 쉽게 확인하고 그들이 없데이트가 되었다면 업데이트된 코드를 쉽게 다운로드 받을수 있게 해줍니다.

이렇게 재사용가능한 코드들의 조각들을 packages(패키지) 또는 때로는 modules(모듈)이라고 부릅니다. 패키지는 그 안에 하나 또는 여러개의 파일을 가지고 있는 단순한 디렉토리이며, 이디렉토리에는 이 패키지에 대한 메타 정보를 가지고 있는 "package.json" 이라고 불리는 파일을 가지고 있습니다. 웹사이트와 같은 전형적인 어플리게이션을 수많은 패키지들에 의존할 것입니다. 이러한 패키지들을 종종 작은데, 하나의 문제를 해결하는 작은 buildin block을 만드는것이 일반적인 생각이기 때문입니다. (. These packages are often small. The general idea is that you create a small building block which solves one problem and solves it well. This makes it possible for you to compose larger, custom solutions out of these small, shared building blocks.)

여기에는 많은 이익이 있습니다. 이는 당신의 팀이 특정 문제 영역에 초점을 둔 사람들이 만든 패키지를 가지고 옴으로써 당신의 기관 밖에서도 전문지식을 얻을 수 있게 합니다. 그러나 당신의 기관 밖의 사람들로부터 코드를 재사용하지 않더라도, 이러한 모듈 기반의 접근은 당신의 팀이 더 협업하기 좋고, 또한 프로젝트 전반에 걸쳐서 코드 재사용을 가능하게 합니다.

당신은 npm 웹사이트를 브라우징 함으로써 당신의 어플리게이션을 만드는데 돕는 패키지들을 찾을 수 있습니다. npm 웹사이트를 브라우징할때, 당신은 다른 종류의 패키지들을 발견할 것입니다. 당신은 많은 노드 모듈들을 찾을 것입니다. npm은 노드 패키지 매니저로써 시작했습니다. 그래서 당신은 서버 사이드에 사용되는 많은 모듈들도 찾아볼 수 있습니다. 또한 npm 웹사이트에는 당신이 커맨드라인을 사용할수 있게 명령을 추가하는 패키지들도 많이 있습니다. 그리도 당신은 브라우저에서 사용되는, 프론트엔드에 사용되는 패키지들도 이시점에선 많이 찾을 수 있습니다.

자, 그럼 당신은 아마 npm이 무엇을 하는지 감이 잡혔을 것이니 npm이 어떻게 동작하는지를 살펴봅시다. 사람들이 npm에 대해 말할때, 그들은 아마 세가지 중 하나에 대해서 말할 것입니다. 첫째는 사람들은 아마도 우리가 지금 보고 있는 웹사이트에 대해서 말할 것입니다. 아니면 그들은 아마 사람들이 공유하고 있는 패키지들에 대한 큰 정보들으 가지고 있는 데이타베이스인 레지스트리에 대해서 말할 수도 있습니다. 세번째는 사람들은 클라이언트에 대해서 말할것 입니다. 클라이언트란 개발자가 그들의 코드를 공유하려고 결정할때, 개발자들은 공유할 코드를 레지스트리에 퍼블리시하기위해 그들은 자신의 컴퓨터에 설치된 npm 클라이언트를 사용하는데 이 클라이언트를 말합니다. 이 패키지를 위한 레지트스리 도입에는 또한 웹사이트에 반영되는데 그곳에서 새로운 패키지에 기여하는 페이지도 있습니다..?(The entry in the registry for this package is also reflected on the website, where there's a page dedicated to this new package.)

자, 이것이 npm 이란 무엇인가 이니다. npm은 다른 개발자의 코드를 재 사용하는 방법이자, 그 안에서 당신의 코드를 공유하는 방법이고, 다른버전의 코드를 관리하기 쉽게하는 것입니다.