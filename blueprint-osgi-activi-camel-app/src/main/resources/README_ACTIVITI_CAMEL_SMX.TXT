Remarques très importantes:
===========================

Les fichiers ".bpmn" doivent être placés dans OSGI-INF/activiti 
pour être chargés automatiquement.

A l'intérieur du .bpmn :
  <serviceTask id="..."   activiti:type="camel"> si camel et activiti sans contexte serviceMix
  MAIS
   <serviceTask id="..."  activiti:delegateExpression="${camel}" >  si camel et activiti dans contexte serviceMix
   (et dans ce cas , pas de activiti:type="camel" ) .
   
   
Si erreur "Query return 2,3 ou n results instead of max 1" 
c'est souvent à cause des echecs précédents (anciennes instances pas terminées et conflit sur "business Key")


En groovy map.subMap.xxx et map['subMap']['xxx'] ok
mais pas en javascript ---> map.get('subMap').get('xxx') et map.put(...,....)


Par défaut pas de timer pris en charge par activiti intégré à serviceMix.

Via le bundle "my-specific-smx-activity-config-bundle" 
(à utiliser à la place de la config "servicemix/activiti" par défaut : osgi:stop , osgi:uninstall de l'ancien
+ copie du nouveau dans "deploy" )
on peut:
     configurer activiti (dans servicemix) pour prendre en  charge les "timer"
     ....

issues à déterminer (pour l'avenir):
======================
* essayer d'ajouter "groovy" dans "serviceMix + activiti" 
   ---> car syntaxe et possibilités de groovy mieux que javascript 
