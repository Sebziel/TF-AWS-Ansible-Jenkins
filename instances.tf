data "aws_ami" "this" {
  filter {
    name   = "architecture"
    values = ["x86_64"]
  }
  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-20240701"]
  }
}

module "ec2_ansible_instance" {
  source  = "terraform-aws-modules/ec2-instance/aws"

  name = "ansible-control"
  ami = data.aws_ami.this.id
  instance_type          = "t2.small"
  monitoring             = true
  vpc_security_group_ids = [module.ssh_sg.security_group_id]
  subnet_id              = module.vpc.public_subnets[0]
  associate_public_ip_address = true
  user_data = base64encode(templatefile("Scripts/ansibleInstall.sh.tftpl", {private_key=module.key_pair.private_key_pem, public_ip=module.ec2_jenkins_instance.public_ip}))
  key_name = module.key_pair.key_pair_name
  tags = {
    Terraform   = "true"
    Environment = "dev"
  }
}

module "ec2_jenkins_instance" {
  source  = "terraform-aws-modules/ec2-instance/aws"

  name = "jenkins-master"
  ami = data.aws_ami.this.id
  instance_type          = "t2.small"
  monitoring             = true
  vpc_security_group_ids = [module.ssh_sg.security_group_id, module.http_sg.security_group_id]
  subnet_id              = module.vpc.public_subnets[0]
  associate_public_ip_address = true
  key_name = module.key_pair.key_pair_name

  tags = {
    Terraform   = "true"
    Environment = "dev"
  }
}