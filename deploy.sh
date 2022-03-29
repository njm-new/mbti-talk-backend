docker_username="njm-new"
image_name="mbti-talk-backend"
container_name="release_container"
version="latest"

git_username="mono404"
git_token="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"

# move working directory
# cd /home/centos/mbti-talk-backend

# remove container
echo "=> Remove previous container..."
docker rm -f ${container_name}

# remove image
echo "=> Remove previous image..."
docker rmi -f ${docker_username}/${image_name}:${version}

# git pull
echo "=> Git pull origin..."
git pull "https://${git_username}:${git_token}@github.com/njm-new/mbti-talk-backend.git" develop

# gradle clean build
echo "=> Gradle clean build..."
./gradlew clean build -x test

echo "## Automation docker-database build and run ##"

# new-build/re-build docker image
echo "=> Build new image..."
docker build --tag ${docker_username}/${image_name}:${version} .

# Run container
echo "=> Run container..."
docker run -d -p 80:3001 --name ${container_name} ${docker_username}/${image_name}:${version}