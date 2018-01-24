kubectl create  ns jenkins
kubectl  create sa jenkins -n jenkins
kubectl create clusterrolebinding jenkins --clusterrole cluster-admin --serviceaccount=jenkins:jenkins -n jenkins
kubectl create -f jenkins-local-volumes.yaml -n jenkins

helm install stable/jenkins  --name jenkins --namespace  jenkins -f  jenkins-values.yaml --debug
