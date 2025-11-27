from selenium import webdriver
from selenium.webdriver.common.keys import Keys

driver = webdriver.Chrome(r"C:\chromedriver.exe")
driver.get('http://www.google.com')
print ('Chrome is opened!!')
