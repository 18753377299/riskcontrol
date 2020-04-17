FROM artifact.piccit.com.cn:8090/paas/public/suse_java:latest

RUN echo "Asia/Shanghai" > /etc/timezone

ARG basedir=/data/release/
ARG tsfdir=/data/tsf/
RUN mkdir -p $basedir
COPY run.sh $tsfdir



WORKDIR /data/release
ADD ./target/riskcontrol-*.jar .

EXPOSE 80

CMD ["sh", "-c", "cd /data/tsf; sh run.sh /data/release/riskcontrol-*.jar /data/tsf"]