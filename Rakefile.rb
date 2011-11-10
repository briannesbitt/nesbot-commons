require 'fileutils'

PROJECT="nesbot-commons"
SRC_MAIN_PATH="src/main/java"
SRC_TEST_PATH="src/test/java"
SRC_MAIN_RESOURCES_PATH="src/main/resources"
SRC_TEST_RESOURCES_PATH="src/test/resources"
OUT_MAIN_PATH="out/production/#{PROJECT}"
OUT_TEST_PATH="out/test/#{PROJECT}"
JAR="out/#{PROJECT}.jar"
COBERTURA_HOME="src/test/resources/cobertura"
COBERTURA_REPORT_PATH="out/coverage-report"
COBERTURA_INSTRUMENTED_OUT="out/instrumented/#{PROJECT}"

def cp(sep = nil)
   sep ||= File::PATH_SEPARATOR
   ret = FileList[SRC_MAIN_RESOURCES_PATH+'/**/*.jar'].join(sep)
end

def cp_test(sep = nil)
   sep ||= File::PATH_SEPARATOR
   ret = FileList[SRC_MAIN_RESOURCES_PATH+'/**/*.jar', SRC_TEST_RESOURCES_PATH+'/**/*.jar', COBERTURA_INSTRUMENTED_OUT, JAR].join(sep)
end

def src(sep = nil)
   sep ||= " "
   ret = FileList[SRC_MAIN_PATH+'/**/*.java'].join(sep)
end

def src_test(sep = nil)
   sep ||= " "
   ret = FileList[SRC_TEST_PATH+'/**/*.java'].join(sep)
end

task :try do
   puts SRC_TEST_RESOURCES_PATH
   puts FileList[SRC_TEST_RESOURCES_PATH+'/**/*.jar'].join(':')
end

desc "Clean deletes all intermediate and final build items"
task :clean do
   remove_dir "out", :force => true
end

desc "Compiles #{SRC_MAIN_PATH} to #{OUT_MAIN_PATH}"
task :compilemain => :clean do
   mkdir_p OUT_MAIN_PATH
   sh "javac -g -d #{OUT_MAIN_PATH} -cp #{cp} #{src}"
end

desc "Compiles #{SRC_TEST_PATH} to #{OUT_TEST_PATH}"
task :compiletest => [:clean, :compilemain, :jars] do
   mkdir_p OUT_TEST_PATH
   sh "javac -g -d #{OUT_TEST_PATH} -cp #{cp_test} #{src_test}"
end

desc "Compile all java src"
task :compile => [:compilemain, :compiletest]

desc "Creates #{JAR}"
task :jars => :compilemain do 
   sh "jar cvf #{JAR} -C #{OUT_MAIN_PATH} . > NUL"
end

desc "Runs tests in all classes Test*"
task :test, :should_compile do

   should_compile ||= true

   Rake::Task[:compile].invoke unless !should_compile

   sep ||= File::PATH_SEPARATOR

   puts ""
   sh "java -cp #{cp_test};#{OUT_TEST_PATH} com.nesbot.commons.tests.DebugRunner com.nesbot.commons" do |ok, res|
      if ! ok
         fail "***** Uh no... Test failed!!"
      end
   end
   puts ""
end

desc "Runs tests with instrumented classes and generates cobertura report"
task :cobertura => :compile do

   sh "#{COBERTURA_HOME}/cobertura-instrument.bat --destination #{COBERTURA_INSTRUMENTED_OUT} #{OUT_MAIN_PATH}"

   Rake::Task[:test].invoke(false)

   sh "#{COBERTURA_HOME}/cobertura-report.bat --format html --destination #{COBERTURA_REPORT_PATH} #{SRC_MAIN_PATH}"
   sh "start #{COBERTURA_REPORT_PATH}/index.html"
end

desc "Gets build via GIT and creates a new jar release"
task :release => [:test, :jars] do

   uncommits = `git status --porcelain`

   fail "\n***** Can't release without a clean git directory !!\n\n" unless uncommits.split.length == 0

   version = `git describe --tags`.strip
   version = version.slice(1..-1) unless version[0] != "v"
   version = version.slice(0..version.rindex('-')-1).sub("-",".")

   mv JAR, JAR.sub(".jar", "-#{version}.jar")
end

task :default => :compile