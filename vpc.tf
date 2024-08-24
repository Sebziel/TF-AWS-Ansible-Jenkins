module "vpc" {
  source = "terraform-aws-modules/vpc/aws"

  name = "my-vpc"
  cidr = var.vpc_cidr_block

  azs             = ["us-east-1a", "us-east-1b", "us-east-1c"]
  public_subnets  = [for i in range(var.public_subnets_count) : cidrsubnet(var.vpc_cidr_block, 8, i)]

  enable_nat_gateway = true
  enable_vpn_gateway = true

  tags = {
    Terraform = "true"
    Environment = "dev"
  }
}