module "iam_assumable_role_inline_policy" {
  source = "terraform-aws-modules/iam/aws//modules/iam-assumable-role"

  trusted_role_services = [
    "ec2.amazonaws.com"
  ]

  create_role = true

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