variable "vpc_cidr_block" {
  description = "CIDR for the VPC"
  default     = "10.0.0.0/16"
  type        = string
}

variable "public_subnets_count" {
  default     = 3
  type        = number
  validation {
    condition     = var.public_subnets_count >= 3
    error_message = "At least 3 subnets are required"
  }
}