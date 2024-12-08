USER="donghojoo083"  # 접속할 사용자명
HOST="35.216.53.231"  # 서버 IP 주소
PORT="22"  # SSH 포트 번호
# shellcheck disable=SC2088
PRIVATE_KEY_PATH="~/.ssh/id_rsa"  # SSH 개인 키 파일 경로 (필요시 수정)

# SSH 접속 명령어
ssh -p $PORT -i $PRIVATE_KEY_PATH $USER@$HOST