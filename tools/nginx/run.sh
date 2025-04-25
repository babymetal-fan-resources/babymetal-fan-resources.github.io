ROOT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )/../.." &> /dev/null && pwd )

docker run -it --rm -d -p 8180:80 --name webBM -v "${ROOT_DIR}/target/site":/usr/share/nginx/html -v "${ROOT_DIR}/tools/nginx/conf.d":/etc/nginx/conf.d nginx