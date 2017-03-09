#!/bin/bash
TOMCAT_HOME='/opt/tomcat'
WEBAPPS_DIR="$TOMCAT_HOME/webapps/"
SEVERINO_WAR='target/severino.war'

cd $(dirname "$0")
echo 'Atualizando com o repositorio do SVN... forneca seu usuario de rede (login.nome)!'

svn up
if [ "$?" -ne "0" ]
then
	echo 'svn up falhou... o que aconteceu?!?!'
	exit 2
fi

echo 'Chamando o maven para construir o projeto...'

mvn clean install -P deploy
if [ "$?" -ne "0" ]
then
	echo 'A build falhou, veja o que esta errado, deploy cancelado!'
	exit 2
fi


if [ -f $SEVERINO_WAR ]
then
	echo "WAR gerado em $SEVERINO_WAR"
else
	echo "WAR nao gerado em $SEVERINO_WAR, deploy cancelado!"
	exit 2
fi

echo 'parando o servico do tomcat ... (entre com a senha de root!)'

ps aux | grep -e "^tomcat"

if [ "$?" -eq "0" ]
then
	echo 'tomcat em execucao, tentando parada...'
	sudo service tomcat stop
	if [ "$?" -ne "0" ]
	then
		echo 'nao foi possivel parar o servico do tomcat'
	fi
fi

echo "Removendo WAR atual ..."
sudo rm -rf ${WEBAPPS_DIR}severino*
mv $SEVERINO_WAR $WEBAPPS_DIR
sudo chown tomcat:tomcat $WEBAPPS_DIR/$(basename $SEVERINO_WAR)
echo "severino.war instalado em $WEBAPPS_DIR"
echo 'iniciando servico do tomcat...'
sudo service tomcat start

if [ "$?" -eq "0" ]
then
	echo 'DEPLOY FINALIZADO COM SUCESSO!'
else
	echo 'Falha ao iniciar o servico, inicie o servico manualmente ou veja o que aconteceu!'
fi
