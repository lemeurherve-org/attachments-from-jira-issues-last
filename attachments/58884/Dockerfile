FROM nginx:latest

# 安装必要运营工具
RUN sed -ri 's/(deb|security)\.debian\.org/mirrors\.aliyun\.com/g' /etc/apt/sources.list \
    && apt-get update \
    && apt-get install -y iputils-ping vim telnet procps net-tools curl --no-install-recommends \
    && rm -rf /var/lib/apt/lists/*

# WORKDIR /fe

# 将项目根目录下dist文件夹下的所有文件复制到镜像中 /usr/share/nginx/html/ 目录下
# TODO 会触发两次git构建，导致构建失败;怀疑原因：与流水线部署后修改git地址有关；
COPY build/ usr/share/nginx/html/
COPY nginx/default.conf etc/nginx/conf.d/default.conf
