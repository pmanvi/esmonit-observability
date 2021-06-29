## API Design
>Microservices is all about having autonomous and self organizing teams and deployable artificat with independent lifecycle to provide productivity, efficiency and effective communication.

###API Design Considerations
* Names (domains, methods, params) be succinct and clear, One job with intent and no room for guessing
* Fail fast & visibly - Limit inflow options and validations(Front door/Back door)
* Hard to misuse is more important than easy to use
* Business(UX) should drive coarse/fine grain apis

###Strive for version-less APIs - "expand and contract"
https://www.martinfowler.com/bliki/ParallelChange.html

###Domain Driven Design
>Domain is an area of knowledge or activity; somebody is responsible and owns it
- Bounded Context
- 
###Trasnactions with cross services
* 2 phase commit
* Saga pattern [![video])](https://www.youtube.com/watch?v=YPbGW3Fnmbc)
