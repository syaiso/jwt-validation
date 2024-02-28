aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 471112651766.dkr.ecr.us-east-1.amazonaws.com
docker build -t jwt-validation -f Dockerfile .
docker tag jwt-validation:latest 471112651766.dkr.ecr.us-east-1.amazonaws.com/jwt-validation:latest
docker push 471112651766.dkr.ecr.us-east-1.amazonaws.com/jwt-validation:latest