### Run with docker

1. ```docker build -t finbitecli-test -f test.Dockerfile .```
2. ```docker run -i finbitecli-test```

### Author comments

- Logging was rushed and not thought out.
- Application has hardcoded EUR. Interface for money would be better or Money and Currency API
- Could use dockerignore for docker build
- Haven't done testing in a long while, got stuck on it, but I think current unit tests cover core logic