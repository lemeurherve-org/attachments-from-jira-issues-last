activeChoiceReactiveReferenceParam('CUSTOM_HTML_TABLE') {
    choiceType('FORMATTED_HTML')
    groovyScript {
        script('''
            def htmlBody = """
                <table>
                    <thead>
                    <tr>
                        <th>Col1</th>
                        <th>Col2</th>
                        <th>Col3</th>
                        <th>Col4</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Row 1</td>
                        <td><input type="hidden" name="value" value="testvalue"><input type="checkbox" name="value" value="value" title="col2"></td>
                        <td><input name="value" class="setting-input" type="text" value="" title="col3"></td>
                        <td><input name="value" class="setting-input" type="checkbox" value="value" title="col4"></td>
                        <input type="hidden" name="value" value="###">
                    </tr>
                    <tr>
                        <td>Row 2</td>
                        <td><input type="hidden" name="value" value="testvalue"><input type="checkbox" name="value" value="value" title="col2"></td>
                        <td><input name="value" class="setting-input" type="text" value="" title="col3"></td>
                        <td><input name="value" class="setting-input" type="checkbox" value="value" title="col4"></td>
                        <input type="hidden" name="value" value="###">
                    </tr>
                    </tbody>
                </table>
            """
            return htmlBody
            '''
        )
        fallbackScript('"Failed to render table"')
    }
    omitValueField(true)
}