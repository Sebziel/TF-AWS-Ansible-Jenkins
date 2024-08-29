# Project still in development. 

# TF-AWS-Ansible-Jenkins
This project automates the setup of a Jenkins server and an Ansible control node using Terraform and Ansible. The infrastructure is provisioned on AWS, and the Jenkins server is configured with necessary plugins and jobs.


## Usage
The project is prepared to be ran on cloud9 AWS environment. run ./TerraformInstall.sh for terraform instalation. Than Terraform Init & terraform apply.
Next step is to connect to EC2 ansible-control instance /home/ubuntu/TF-AWS-Ansible-Jenkins/Ansible and run the playbook with following command: ```ansible-playbook -b -i inventory playbook.yaml ```

## Prerequisites

- AWS account with necessary permissions.
- AWS CLI configured.

## Project Structure


- **playbook.yaml**: Ansible playbook to install and configure Jenkins on the Jenkins server.


### Terraform

- **terraformInstall.sh**: Shell script to install Terraform in an AWS Cloud9 environment.

- **/Scripts/ansibleInstall.sh.tftpl**: Terraform template file used as user_data in EC2 instance for ansible configuration. 
- **network.tf**: Terraform configuration to set up the VPC, subnets, and security groups.
- **variables.tf**: Terraform variables used in the project.
- **kp.tf**: Terraform configuration to create an SSH key pair.
- **instances.tf**: Terraform configuration to create EC2 instances for Jenkins and Ansible.
- **main.tf**: Main Terraform configuration file to set up the AWS provider.

### Expected outcome:
* Ansible-control EC2 Instance. Configured with ansible installed and ssh-keys set up to be used with Jenkins-Master instance.
    * A repository downloaded with ansible inventory, config and playbook downloaded in /home/ubuntu/TF-AWS-Ansible-Jenkins/Ansible directory.
* Jenkins-Master EC2 Instance. Blank instance to be configured with Ansible Playbook. 

### Ansible

In this project Ansible comes configured by terraform. Both ansible.cfg and inventory files are generated automatically.

- **playbook.yaml**: Ansible playbook to install and configure Jenkins on the Jenkins server.

Summary of the actions performed by the playbook.

Updates the apt cache.
Installs Java.
Downloads and adds the Jenkins repository key.
Adds the Jenkins repository to the sources list.
Installs Jenkins.
Ensures necessary directories exist.
Creates a Jenkins service override file.
Reloads the systemd daemon.
Copies Groovy scripts to the Jenkins server.
Restarts the Jenkins service.

### Jenkins

To Do