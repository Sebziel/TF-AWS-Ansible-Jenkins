module "iam_assumable_role_inline_policy" {
  source = "terraform-aws-modules/iam/aws//modules/iam-assumable-role"

  trusted_role_services = [
    "ec2.amazonaws.com"
  ]

  create_role = true
  create_instance_profile = true
  role_name  = "terraform-ecr-role"
  role_requires_mfa = false

  inline_policy_statements = [
    {
      sid = "AllowECRPushPull"
      actions = [
        "ecr:*",
      ]
      effect    = "Allow"
      resources = ["*"]
    }
  ]
}

module "ecr" {
  source = "terraform-aws-modules/ecr/aws"

  repository_name = "email-spring"

  repository_lifecycle_policy = jsonencode({
    rules = [
      {
        rulePriority = 1,
        description  = "Keep last 30 images",
        selection = {
          tagStatus     = "tagged",
          tagPrefixList = ["v"],
          countType     = "imageCountMoreThan",
          countNumber   = 30
        },
        action = {
          type = "expire"
        }
      }
    ]
  })

  tags = {
    Terraform   = "true"
    Environment = "dev"
  }
}