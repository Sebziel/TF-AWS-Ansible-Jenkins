variable "credentials_file_location" {
  type    = list(string)
  default = ["/home/ec2-user/.aws/credentials"]
}

terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "5.64.0"
    }
  }
}

provider "aws" {
  region = "us-east-1"
  shared_credentials_files = var.credentials_file_location
}