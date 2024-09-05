import groovy.json.JsonSlurper

def call() {
    def jsonSlurper = new JsonSlurper()
    def url = new URL("https://api.github.com/repos/Sebziel/spring-petclinic/branches")
    def content = jsonSlurper.parseText(url.text)
    def branchNames = content.name
    return branchNames
}